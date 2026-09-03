const BASE_URL = '/api/financial-management'

export async function getFinancialAccounts(userId) {
  const params = new URLSearchParams({ userId })

  const response = await fetch(`${BASE_URL}?${params.toString()}`, {
    method: 'GET',
  })

  if (!response.ok) {
    throw new Error('No se pudieron cargar las cuentas')
  }
  return response.json()
}

export async function createFinancialAccount(userId, account) {
  const params = new URLSearchParams({ userId })

  const response = await fetch(`${BASE_URL}?${params.toString()}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(account),
  })

  if (!response.ok) {
    throw new Error('No se pudo crear la cuenta')
  }
  return response.json()
}

export async function deleteFinancialAccount(accountId) {
  const params = new URLSearchParams({ financialAccountId: accountId })

  const response = await fetch(`${BASE_URL}/${accountId}?${params.toString()}`, {
    method: 'DELETE',
  })

  if (!response.ok) {
    throw new Error('No se pudo eliminar la cuenta')
  }
  return response.json()
}
