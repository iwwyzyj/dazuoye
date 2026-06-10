<script setup>
import { ref } from 'vue'
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
    <form class="practice-form" @submit.prevent="submit">
      <label>
        编程语言
        <select v-model="language">
          <option v-for="lang in languages" :key="lang" :value="lang">{{ lang }}</option>
        </select>
      </label>

      <label>
        知识点
        <select v-model="topic">
          <option v-for="item in topics" :key="item" :value="item">{{ item }}</option>
        </select>
      </label>

      <label>
        难度 (1-5)
        <input v-model.number="difficulty" type="range" min="1" max="5" />
        <span class="range-value">{{ difficulty }}</span>
      </label>

      <label>
        题量 (1-5)
        <input v-model.number="count" type="number" min="1" max="5" />
      </label>

      <button class="primary-btn" type="submit" :disabled="loading">
        {{ loading ? '生成中...' : '生成练习题' }}
      </button>
    </form>

    <div class="practice-results">
      <h3>练习题列表</h3>

      <div v-if="loading" class="state-box loading-box">正在根据你的选择生成个性化练习题...</div>
      <div v-else-if="error" class="state-box error-box">{{ error }}</div>
      <div v-else-if="problems.length === 0" class="state-box empty-box">
        选择语言、知识点和难度后，系统将生成配套练习。
      </div>

      <article v-for="(problem, index) in problems" :key="index" class="problem-card">
        <header>
          <span class="problem-index">#{{ index + 1 }}</span>
          <h4>{{ problem.title }}</h4>
        </header>
        <p class="problem-desc">{{ problem.description }}</p>
        <div class="problem-meta">
          <div><strong>提示：</strong>{{ problem.hint }}</div>
          <div><strong>示例输入：</strong><code>{{ problem.sampleInput }}</code></div>
          <div><strong>示例输出：</strong><code>{{ problem.sampleOutput }}</code></div>
        </div>
        <button class="ghost-btn" type="button" @click="toggleAnswer(index)">
          {{ expanded[index] ? '隐藏参考答案' : '查看参考答案' }}
        </button>
        <pre v-if="expanded[index]" class="answer-block"><code>{{ problem.referenceAnswer }}</code></pre>
      </article>
    </div>
  </div>
</template>
