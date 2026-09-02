import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'
import { createFinancialAccount, getFinancialAccounts } from '../services/financialAccountsApi'
import { getTransactions } from '../services/transactionsApi'
import './AccountsPage.css'

const emptyAccountForm = { name: '', amount: '' }

function currency(value) {
  const n = Number(value)
  return n.toLocaleString('es-CO', { style: 'currency', currency: 'USD', maximumFractionDigits: 2 })
}

export default function AccountsPage() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  const [accounts, setAccounts] = useState([])
  const [accountsLoading, setAccountsLoading] = useState(true)
  const [accountsError, setAccountsError] = useState(null)

  const [transactionsByAccount, setTransactionsByAccount] = useState({})
  const [transactionsLoading, setTransactionsLoading] = useState({})
  const [transactionsError, setTransactionsError] = useState({})

  const [showNewAccountForm, setShowNewAccountForm] = useState(false)
  const [newAccountForm, setNewAccountForm] = useState(emptyAccountForm)
  const [creatingAccount, setCreatingAccount] = useState(false)
  const [newAccountError, setNewAccountError] = useState(null)

  useEffect(() => {
    let cancelled = false

    async function loadAccounts() {
      setAccountsLoading(true)
      setAccountsError(null)
      try {
        const data = await getFinancialAccounts(user.id)
        if (!cancelled) setAccounts(data)
      } catch (err) {
        if (!cancelled) setAccountsError(err.message)
      } finally {
        if (!cancelled) setAccountsLoading(false)
      }
    }

    loadAccounts()
    return () => {
      cancelled = true
    }
  }, [user.id])

  useEffect(() => {
    if (accounts.length === 0) return
    let cancelled = false

    accounts.forEach((account) => {
      setTransactionsLoading((prev) => ({ ...prev, [account.id]: true }))
      setTransactionsError((prev) => ({ ...prev, [account.id]: null }))

      getTransactions(user.id, account.id)
        .then((data) => {
          if (cancelled) return
          setTransactionsByAccount((prev) => ({ ...prev, [account.id]: data }))
        })
        .catch((err) => {
          if (cancelled) return
          setTransactionsError((prev) => ({ ...prev, [account.id]: err.message }))
        })
        .finally(() => {
          if (cancelled) return
          setTransactionsLoading((prev) => ({ ...prev, [account.id]: false }))
        })
    })

    return () => {
      cancelled = true
    }
  }, [accounts, user.id])

  function handleLogout() {
    logout()
    navigate('/login', { replace: true })
  }

  function goToNewTransaction(accountId) {
    navigate('/transacciones', { state: { accountId } })
  }

  function updateNewAccountField(field, value) {
    setNewAccountForm((f) => ({ ...f, [field]: value }))
  }

  function toggleNewAccountForm() {
    setShowNewAccountForm((v) => !v)
    setNewAccountError(null)
    setNewAccountForm(emptyAccountForm)
  }

  async function handleCreateAccount(e) {
    e.preventDefault()
    setCreatingAccount(true)
    setNewAccountError(null)

    try {
      const created = await createFinancialAccount(user.id, {
        name: newAccountForm.name,
        amount: Number(newAccountForm.amount),
      })
      setAccounts((prev) => [...prev, created])
      setShowNewAccountForm(false)
      setNewAccountForm(emptyAccountForm)
    } catch (err) {
      setNewAccountError(err.message || 'No se pudo crear la cuenta')
    } finally {
      setCreatingAccount(false)
    }
  }

  return (
    <div className="acc-screen">
      <header className="acc-topbar">
        <div className="acc-brand">
          <svg viewBox="0 0 32 32" fill="none">
            <rect width="32" height="32" rx="9" fill="url(#g3)" />
            <path d="M8 20.5 13 15l4 3.5L24 10" stroke="#0b1220" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round" />
            <path d="M19 10h5v5" stroke="#0b1220" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round" />
            <defs>
              <linearGradient id="g3" x1="0" y1="0" x2="32" y2="32">
                <stop stopColor="#34e0a1" />
                <stop offset="1" stopColor="#22c1c3" />
              </linearGradient>
            </defs>
          </svg>
          <span>Finzo</span>
        </div>
        <div className="acc-user">
          <span>{user.email}</span>
          <button type="button" onClick={handleLogout}>
            Cerrar sesión
          </button>
        </div>
      </header>

      <main className="acc-main">
        <div className="acc-main-header">
          <h1>Tus cuentas</h1>
          <button type="button" className="acc-new-toggle" onClick={toggleNewAccountForm}>
            {showNewAccountForm ? 'Cancelar' : '+ Nueva cuenta'}
          </button>
        </div>

        {showNewAccountForm && (
          <form className="acc-new-form" onSubmit={handleCreateAccount}>
            <label className="acc-field">
              <span>Nombre</span>
              <input
                required
                value={newAccountForm.name}
                onChange={(e) => updateNewAccountField('name', e.target.value)}
                placeholder="Ej. Cuenta de ahorros"
              />
            </label>

            <label className="acc-field">
              <span>Saldo inicial</span>
              <input
                required
                type="number"
                step="0.01"
                min="0"
                value={newAccountForm.amount}
                onChange={(e) => updateNewAccountField('amount', e.target.value)}
                placeholder="0.00"
              />
            </label>

            {newAccountError && <p className="acc-message error">{newAccountError}</p>}

            <button type="submit" className="acc-new-submit" disabled={creatingAccount}>
              {creatingAccount ? 'Creando…' : 'Crear cuenta'}
            </button>
          </form>
        )}

        {accountsLoading && <p className="acc-hint">Cargando cuentas…</p>}
        {accountsError && <p className="acc-message error">{accountsError}</p>}

        {!accountsLoading && !accountsError && accounts.length === 0 && !showNewAccountForm && (
          <p className="acc-message notice">
            Todavía no tienes cuentas financieras registradas.
          </p>
        )}

        <div className="acc-grid">
          {accounts.map((account) => {
            const transactions = transactionsByAccount[account.id] ?? []
            const loading = transactionsLoading[account.id]
            const error = transactionsError[account.id]

            return (
              <section key={account.id} className="acc-card">
                <div className="acc-card-header">
                  <div>
                    <h2>{account.name}</h2>
                    <span className="acc-card-balance">{currency(account.amount)}</span>
                  </div>
                  <button
                    type="button"
                    className="acc-card-add"
                    onClick={() => goToNewTransaction(account.id)}
                  >
                    + Transacción
                  </button>
                </div>

                {loading && <p className="acc-hint">Cargando movimientos…</p>}
                {error && <p className="acc-message error">{error}</p>}

                {!loading && !error && transactions.length === 0 && (
                  <p className="acc-hint">Aún no hay transacciones en esta cuenta.</p>
                )}

                {transactions.length > 0 && (
                  <ul className="acc-tx-list">
                    {transactions.map((t) => (
                      <li key={t.id} className="acc-tx-row">
                        <div className="acc-tx-row-main">
                          <span className="acc-tx-row-name">{t.name}</span>
                          <span className="acc-tx-row-meta">
                            {t.category?.name ?? 'General'} {t.merchant ? `· ${t.merchant}` : ''}
                          </span>
                        </div>
                        <span className={`acc-tx-row-amount ${t.transactionType === 'INCOME' ? 'income' : 'expense'}`}>
                          {t.transactionType === 'INCOME' ? '+' : '-'}
                          {currency(t.amount)}
                        </span>
                      </li>
                    ))}
                  </ul>
                )}
              </section>
            )
          })}
        </div>
      </main>
    </div>
  )
}
