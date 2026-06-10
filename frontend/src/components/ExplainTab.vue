<script setup>
import { ref } from 'vue'
import { marked } from 'marked'
import { explainCode } from '../api/tutor'

const languages = ['Java', 'Python', 'C', 'C++', 'JavaScript']
const levels = [
  { value: 'beginner', label: '入门' },
  { value: 'intermediate', label: '中级' },
  { value: 'advanced', label: '进阶' }
]

const language = ref('Java')
const level = ref('beginner')
const code = ref(`public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}`)
const loading = ref(false)
const error = ref('')
const resultHtml = ref('')

async function submit() {
  error.value = ''
  resultHtml.value = ''
  loading.value = true
  try {
    const data = await explainCode({ language: language.value, code: code.value, level: level.value })
    resultHtml.value = marked.parse(data.result || '')
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="tab-layout">
    <div class="form-column">
      <div class="field-row">
        <label>
          编程语言
          <select v-model="language">
            <option v-for="lang in languages" :key="lang" :value="lang">{{ lang }}</option>
          </select>
        </label>
        <label>
          解释深度
          <select v-model="level">
            <option v-for="item in levels" :key="item.value" :value="item.value">{{ item.label }}</option>
          </select>
        </label>
      </div>

      <label class="block-label">
        代码
        <textarea v-model="code" rows="14" spellcheck="false" placeholder="粘贴或输入需要解释的代码..." />
      </label>

      <button class="primary-btn" :disabled="loading || !code.trim()" @click="submit">
        {{ loading ? '分析中...' : '开始解释' }}
      </button>
    </div>

    <div class="result-column">
      <h3>解释结果</h3>
      <div v-if="loading" class="state-box loading-box">正在调用智谱 AI 分析代码，请稍候...</div>
      <div v-else-if="error" class="state-box error-box">{{ error }}</div>
      <div v-else-if="resultHtml" class="markdown-body" v-html="resultHtml" />
      <div v-else class="state-box empty-box">提交代码后，AI 将逐段解释其功能与核心概念。</div>
    </div>
  </div>
</template>
