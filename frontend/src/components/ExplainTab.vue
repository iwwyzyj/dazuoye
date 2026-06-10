<script setup>
import { ref } from 'vue'
import { marked } from 'marked'
import { explainCode } from '../api/tutor'
import ResultPanel from './ResultPanel.vue'

marked.setOptions({ breaks: true })

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

function clearCode() {
  code.value = ''
}
</script>

<template>
  <div class="tab-layout">
    <div class="form-column card-block">
      <div class="block-title">输入代码</div>
      <div class="field-row">
        <label>
          <span class="label-text">编程语言</span>
          <select v-model="language">
            <option v-for="lang in languages" :key="lang" :value="lang">{{ lang }}</option>
          </select>
        </label>
        <label>
          <span class="label-text">解释深度</span>
          <select v-model="level">
            <option v-for="item in levels" :key="item.value" :value="item.value">{{ item.label }}</option>
          </select>
        </label>
      </div>

      <label class="block-label code-label">
        <span class="label-text">源代码</span>
        <div class="code-editor-wrap">
          <textarea v-model="code" rows="14" spellcheck="false" placeholder="// 粘贴或输入需要解释的代码..." />
        </div>
      </label>

      <div class="action-row">
        <button class="primary-btn" :disabled="loading || !code.trim()" @click="submit">
          <span v-if="loading" class="btn-spinner" />
          {{ loading ? 'AI 分析中...' : '开始解释' }}
        </button>
        <button class="ghost-btn" type="button" :disabled="loading" @click="clearCode">清空</button>
      </div>
    </div>

    <ResultPanel
      title="解释结果"
      hint="AI 将按整体功能、逐段说明、核心概念展开"
      :loading="loading"
      loading-text="正在调用智谱 AI 分析代码..."
      :error="error"
      empty="提交代码后，这里会显示结构化的中文解释。"
    >
      <div class="markdown-body" v-html="resultHtml" />
    </ResultPanel>
  </div>
</template>
