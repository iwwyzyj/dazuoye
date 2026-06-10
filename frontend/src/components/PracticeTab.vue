<script setup>
import { ref, computed } from 'vue'
import { generatePractice } from '../api/tutor'

const languages = ['Java', 'Python', 'C', 'JavaScript']
const topics = ['变量与数据类型', '条件语句', '循环结构', '数组', '函数/方法', '面向对象基础']

const language = ref('Java')
const topic = ref('循环结构')
const difficulty = ref(2)
const count = ref(3)
const loading = ref(false)
const error = ref('')
const problems = ref([])
const expanded = ref({})

const difficultyLabel = computed(() => {
  const labels = ['', '非常简单', '简单', '中等', '较难', '挑战']
  return labels[difficulty.value] || '中等'
})

async function submit() {
  error.value = ''
  problems.value = []
  expanded.value = {}
  loading.value = true
  try {
    const data = await generatePractice({
      language: language.value,
      topic: topic.value,
      difficulty: difficulty.value,
      count: count.value
    })
    problems.value = data.problems || []
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}

function toggleAnswer(index) {
  expanded.value[index] = !expanded.value[index]
}
</script>

<template>
  <div class="practice-layout">
    <form class="practice-form card-block" @submit.prevent="submit">
      <div class="block-title">练习设置</div>
      <div class="practice-fields">
        <label>
          <span class="label-text">编程语言</span>
          <select v-model="language">
            <option v-for="lang in languages" :key="lang" :value="lang">{{ lang }}</option>
          </select>
        </label>

        <label>
          <span class="label-text">知识点</span>
          <select v-model="topic">
            <option v-for="item in topics" :key="item" :value="item">{{ item }}</option>
          </select>
        </label>

        <label class="range-label">
          <span class="label-text">难度 · {{ difficultyLabel }}</span>
          <div class="range-row">
            <input v-model.number="difficulty" type="range" min="1" max="5" />
            <span class="range-value">{{ difficulty }}</span>
          </div>
        </label>

        <label>
          <span class="label-text">题量</span>
          <input v-model.number="count" type="number" min="1" max="5" />
        </label>
      </div>

      <button class="primary-btn" type="submit" :disabled="loading">
        <span v-if="loading" class="btn-spinner" />
        {{ loading ? '生成中...' : '生成练习题' }}
      </button>
    </form>

    <div class="practice-results">
      <div class="results-header">
        <h3>练习题列表</h3>
        <span v-if="problems.length" class="count-badge">{{ problems.length }} 题</span>
      </div>

      <div v-if="loading" class="state-box loading-box">
        <div class="spinner" />
        <span>正在根据你的选择生成个性化练习题...</span>
      </div>
      <div v-else-if="error" class="state-box error-box">
        <span class="state-icon">⚠️</span>
        <div>
          <strong>生成失败</strong>
          <p>{{ error }}</p>
        </div>
      </div>
      <div v-else-if="problems.length === 0" class="state-box empty-box">
        <span class="state-icon">📝</span>
        <p>选择语言、知识点和难度后，点击生成即可获得配套练习。</p>
      </div>

      <div v-else class="problem-grid">
        <article v-for="(problem, index) in problems" :key="index" class="problem-card">
          <header>
            <span class="problem-index">#{{ index + 1 }}</span>
            <h4>{{ problem.title }}</h4>
          </header>
          <p class="problem-desc">{{ problem.description }}</p>
          <div class="problem-meta">
            <div class="meta-item">
              <span class="meta-key">提示</span>
              <span>{{ problem.hint }}</span>
            </div>
            <div class="meta-item io-item">
              <span class="meta-key">输入</span>
              <code>{{ problem.sampleInput }}</code>
            </div>
            <div class="meta-item io-item">
              <span class="meta-key">输出</span>
              <code>{{ problem.sampleOutput }}</code>
            </div>
          </div>
          <button class="ghost-btn" type="button" @click="toggleAnswer(index)">
            {{ expanded[index] ? '▲ 隐藏答案' : '▼ 查看参考答案' }}
          </button>
          <pre v-if="expanded[index]" class="answer-block"><code>{{ problem.referenceAnswer }}</code></pre>
        </article>
      </div>
    </div>
  </div>
</template>
