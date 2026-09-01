const BASE_URL = `http://${window.location.hostname}:8080/api/financial-management`

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
