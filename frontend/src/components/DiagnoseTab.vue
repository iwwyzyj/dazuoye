<script setup>
import { ref } from 'vue'
import { marked } from 'marked'
import { diagnoseCode } from '../api/tutor'
import ResultPanel from './ResultPanel.vue'

marked.setOptions({ breaks: true })

const languages = ['Java', 'Python', 'C', 'C++', 'JavaScript']

const language = ref('Java')
const code = ref(`public class Sum {
    public static void main(String[] args) {
        int a = 10
        int b = 20;
        System.out.println(a + b);
    }
}`)
const errorMessage = ref(`Sum.java:3: error: ';' expected
        int a = 10
                 ^`)
const loading = ref(false)
const error = ref('')
const resultHtml = ref('')

async function submit() {
  error.value = ''
  resultHtml.value = ''
  loading.value = true
  try {
    const data = await diagnoseCode({
      language: language.value,
      code: code.value,
      errorMessage: errorMessage.value
    })
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
    <div class="form-column card-block">
      <div class="block-title">报错信息</div>
      <label>
        <span class="label-text">编程语言</span>
        <select v-model="language">
          <option v-for="lang in languages" :key="lang" :value="lang">{{ lang }}</option>
        </select>
      </label>

      <label class="block-label code-label">
        <span class="label-text">源代码</span>
        <div class="code-editor-wrap">
          <textarea v-model="code" rows="9" spellcheck="false" placeholder="// 粘贴出错的代码..." />
        </div>
      </label>

      <label class="block-label">
        <span class="label-text">错误信息</span>
        <textarea
          v-model="errorMessage"
          class="error-input"
          rows="5"
          spellcheck="false"
          placeholder="粘贴编译器或运行时的报错..."
        />
      </label>

      <div class="action-row">
        <button class="primary-btn" :disabled="loading || !code.trim() || !errorMessage.trim()" @click="submit">
          <span v-if="loading" class="btn-spinner" />
          {{ loading ? '诊断中...' : '开始诊断' }}
        </button>
      </div>
    </div>

    <ResultPanel
      title="诊断结果"
      hint="包含错误原因、修正方案与避坑建议"
      :loading="loading"
      loading-text="正在分析错误并生成修正建议..."
      :error="error"
      empty="提供代码和报错后，AI 会给出通俗易懂的修复指导。"
    >
      <div class="markdown-body" v-html="resultHtml" />
    </ResultPanel>
  </div>
</template>
