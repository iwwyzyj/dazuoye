<script setup>
import { ref } from 'vue'
import { marked } from 'marked'
import { diagnoseCode } from '../api/tutor'

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
    <div class="form-column">
      <label>
        编程语言
        <select v-model="language">
          <option v-for="lang in languages" :key="lang" :value="lang">{{ lang }}</option>
        </select>
      </label>

      <label class="block-label">
        源代码
        <textarea v-model="code" rows="10" spellcheck="false" placeholder="粘贴出错的代码..." />
      </label>

      <label class="block-label">
        错误信息
        <textarea v-model="errorMessage" rows="6" spellcheck="false" placeholder="粘贴编译器或运行时的报错信息..." />
      </label>

      <button class="primary-btn" :disabled="loading || !code.trim() || !errorMessage.trim()" @click="submit">
        {{ loading ? '诊断中...' : '开始诊断' }}
      </button>
    </div>

    <div class="result-column">
      <h3>诊断结果</h3>
      <div v-if="loading" class="state-box loading-box">正在分析错误原因并生成修正建议...</div>
      <div v-else-if="error" class="state-box error-box">{{ error }}</div>
      <div v-else-if="resultHtml" class="markdown-body" v-html="resultHtml" />
      <div v-else class="state-box empty-box">提供代码与报错信息后，系统将给出原因分析与修正方案。</div>
    </div>
  </div>
</template>
