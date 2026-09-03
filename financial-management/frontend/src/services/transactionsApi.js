const BASE_URL = '/api/transactions'

export async function getTransactions(userId, financialAccountId) {
  const params = new URLSearchParams({ userid: userId, financialAccountId })

  const response = await fetch(`${BASE_URL}?${params.toString()}`, {
    method: 'GET',
  })

  if (!response.ok) {
    throw new Error('No se pudieron cargar las transacciones')
  }
  return response.json()
}

export async function createTransaction(userId, transaction) {
  const params = new URLSearchParams({ userId })

  const response = await fetch(`${BASE_URL}?${params.toString()}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(transaction),
  })

  if (!response.ok) {
    throw new Error('No se pudo crear la transacción')
  }
  return response.json()
}

export async function deleteTransaction(transactionId, financialAccountId) {
  const params = new URLSearchParams({ financialAccountId })

  const response = await fetch(`${BASE_URL}/${transactionId}?${params.toString()}`, {
    method: 'DELETE',
  })

  if (!response.ok) {
    throw new Error('No se pudo eliminar la transacción')
  }
  return response.json()
}
