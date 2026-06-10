const BASE = '/api'

async function request(path, body) {
  const response = await fetch(`${BASE}${path}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  })

  const data = await response.json().catch(() => ({}))
  if (!response.ok) {
    throw new Error(data.message || `请求失败 (${response.status})`)
  }
  return data
}

export function explainCode(payload) {
  return request('/explain', payload)
}

export function diagnoseCode(payload) {
  return request('/diagnose', payload)
}

export function generatePractice(payload) {
  return request('/practice/generate', payload)
}
