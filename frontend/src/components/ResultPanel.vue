<script setup>
defineProps({
  title: { type: String, required: true },
  hint: { type: String, default: '' },
  loading: { type: Boolean, default: false },
  loadingText: { type: String, default: '处理中，请稍候...' },
  error: { type: String, default: '' },
  empty: { type: String, default: '' }
})
</script>

<template>
  <div class="result-panel">
    <div class="result-header">
      <h3>{{ title }}</h3>
      <p v-if="hint">{{ hint }}</p>
    </div>
    <div class="result-body">
      <div v-if="loading" class="state-box loading-box">
        <div class="spinner" />
        <span>{{ loadingText }}</span>
      </div>
      <div v-else-if="error" class="state-box error-box">
        <span class="state-icon">⚠️</span>
        <div>
          <strong>请求失败</strong>
          <p>{{ error }}</p>
        </div>
      </div>
      <div v-else-if="$slots.default" class="result-content">
        <slot />
      </div>
      <div v-else class="state-box empty-box">
        <span class="state-icon">💡</span>
        <p>{{ empty }}</p>
      </div>
    </div>
  </div>
</template>
