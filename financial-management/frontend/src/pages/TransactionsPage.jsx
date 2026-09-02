import { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'
import { getFinancialAccounts } from '../services/financialAccountsApi'
import { createTransaction, getTransactions } from '../services/transactionsApi'
import './TransactionsPage.css'

const emptyForm = {
  name: '',
  amount: '',
  category: '',
  merchant: '',
  source: '',
  transactionType: 'EXPENSE',
  isRecurring: false,
}

function currency(value) {
  const n = Number(value)
  return n.toLocaleString('es-CO', { style: 'currency', currency: 'USD', maximumFractionDigits: 2 })
}

export default function TransactionsPage() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()
  const preselectedAccountId = location.state?.accountId

  const [accounts, setAccounts] = useState([])
  const [accountsLoading, setAccountsLoading] = useState(true)
  const [accountsError, setAccountsError] = useState(null)
  const [selectedAccountId, setSelectedAccountId] = useState('')

  const [transactions, setTransactions] = useState([])
  const [listLoading, setListLoading] = useState(false)
  const [listError, setListError] = useState(null)

  const [form, setForm] = useState(emptyForm)
  const [saving, setSaving] = useState(false)
  const [formError, setFormError] = useState(null)

  useEffect(() => {
    let cancelled = false

    async function loadAccounts() {
      setAccountsLoading(true)
      setAccountsError(null)
      try {
        const data = await getFinancialAccounts(user.id)
        if (cancelled) return
        setAccounts(data)
        const preselected = data.find((a) => String(a.id) === String(preselectedAccountId))
        if (preselected) setSelectedAccountId(String(preselected.id))
        else if (data.length > 0) setSelectedAccountId(String(data[0].id))
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
  }, [user.id, preselectedAccountId])

  useEffect(() => {
    let cancelled = false

    async function loadTransactions() {
      if (!selectedAccountId) {
        setTransactions([])
        return
      }
      setListLoading(true)
      setListError(null)
      try {
        const data = await getTransactions(user.id, selectedAccountId)
        if (!cancelled) setTransactions(data)
      } catch (err) {
        if (!cancelled) setListError(err.message)
      } finally {
        if (!cancelled) setListLoading(false)
      }
    }

    loadTransactions()
    return () => {
      cancelled = true
    }
  }, [user.id, selectedAccountId])

  function updateField(field, value) {
    setForm((f) => ({ ...f, [field]: value }))
  }

  async function handleCreate(e) {
    e.preventDefault()
    if (!selectedAccountId) return

    setSaving(true)
    setFormError(null)

    try {
      const created = await createTransaction(user.id, {
        name: form.name,
        amount: Number(form.amount),
        financialAccountId: Number(selectedAccountId),
        category: { name: form.category || 'General' },
        merchant: form.merchant,
        source: form.source,
        transactionType: form.transactionType,
        isRecurring: form.isRecurring,
      })
      setTransactions((prev) => [created, ...prev])
      setForm(emptyForm)
    } catch (err) {
      setFormError(err.message || 'No se pudo crear la transacción')
    } finally {
      setSaving(false)
    }
  }

  function handleLogout() {
    logout()
    navigate('/login', { replace: true })
  }

  const selectedAccount = accounts.find((a) => String(a.id) === String(selectedAccountId))

  return (
    <div className="tx-screen">
      <header className="tx-topbar">
        <div className="tx-brand">
          <svg viewBox="0 0 32 32" fill="none">
            <rect width="32" height="32" rx="9" fill="url(#g2)" />
            <path d="M8 20.5 13 15l4 3.5L24 10" stroke="#0b1220" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round" />
            <path d="M19 10h5v5" stroke="#0b1220" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round" />
            <defs>
              <linearGradient id="g2" x1="0" y1="0" x2="32" y2="32">
                <stop stopColor="#34e0a1" />
                <stop offset="1" stopColor="#22c1c3" />
              </linearGradient>
            </defs>
          </svg>
          <span>Finzo</span>
        </div>
        <div className="tx-user">
          <button type="button" onClick={() => navigate('/cuentas')}>
            Mis cuentas
          </button>
          <span>{user.email}</span>
          <button type="button" onClick={handleLogout}>
            Cerrar sesión
          </button>
        </div>
      </header>

      <main className="tx-main">
        <h1>Transacciones</h1>

        {accountsLoading && <p className="tx-hint">Cargando cuentas…</p>}
        {accountsError && <p className="tx-message error">{accountsError}</p>}

        {!accountsLoading && !accountsError && accounts.length === 0 && (
          <p className="tx-message notice">
            Todavía no tienes cuentas financieras. Necesitas al menos una cuenta para poder
            registrar transacciones.
          </p>
        )}

        {accounts.length > 0 && (
          <div className="tx-layout">
            <section className="tx-card tx-form-card">
              <h2>Nueva transacción</h2>

              <label className="tx-field">
                <span>Cuenta</span>
                <select
                  value={selectedAccountId}
                  onChange={(e) => setSelectedAccountId(e.target.value)}
                >
                  {accounts.map((acc) => (
                    <option key={acc.id} value={acc.id}>
                      {acc.name} · {currency(acc.amount)}
                    </option>
                  ))}
                </select>
              </label>

              <form className="tx-form" onSubmit={handleCreate}>
                <label className="tx-field">
                  <span>Descripción</span>
                  <input
                    required
                    value={form.name}
                    onChange={(e) => updateField('name', e.target.value)}
                    placeholder="Ej. Supermercado"
                  />
                </label>

                <div className="tx-field-row">
                  <label className="tx-field">
                    <span>Monto</span>
                    <input
                      required
                      type="number"
                      step="0.01"
                      min="0"
                      value={form.amount}
                      onChange={(e) => updateField('amount', e.target.value)}
                      placeholder="0.00"
                    />
                  </label>

                  <label className="tx-field">
                    <span>Tipo</span>
                    <select
                      value={form.transactionType}
                      onChange={(e) => updateField('transactionType', e.target.value)}
                    >
                      <option value="EXPENSE">Gasto</option>
                      <option value="INCOME">Ingreso</option>
                    </select>
                  </label>
                </div>

                <div className="tx-field-row">
                  <label className="tx-field">
                    <span>Categoría</span>
                    <input
                      value={form.category}
                      onChange={(e) => updateField('category', e.target.value)}
                      placeholder="Ej. Alimentación"
                    />
                  </label>

                  <label className="tx-field">
                    <span>Comercio</span>
                    <input
                      value={form.merchant}
                      onChange={(e) => updateField('merchant', e.target.value)}
                      placeholder="Ej. Éxito"
                    />
                  </label>
                </div>

                <label className="tx-field">
                  <span>Fuente</span>
                  <input
                    value={form.source}
                    onChange={(e) => updateField('source', e.target.value)}
                    placeholder="Ej. Tarjeta débito"
                  />
                </label>

                <label className="tx-checkbox">
                  <input
                    type="checkbox"
                    checked={form.isRecurring}
                    onChange={(e) => updateField('isRecurring', e.target.checked)}
                  />
                  <span>Es una transacción recurrente</span>
                </label>

                {formError && <p className="tx-message error">{formError}</p>}

                <button type="submit" className="tx-submit" disabled={saving}>
                  {saving ? 'Guardando…' : 'Registrar transacción'}
                </button>
              </form>
            </section>

            <section className="tx-card tx-list-card">
              <div className="tx-list-header">
                <h2>Movimientos</h2>
                {selectedAccount && <span className="tx-account-name">{selectedAccount.name}</span>}
              </div>

              {listLoading && <p className="tx-hint">Cargando movimientos…</p>}
              {listError && <p className="tx-message error">{listError}</p>}

              {!listLoading && !listError && transactions.length === 0 && (
                <p className="tx-hint">Aún no hay transacciones registradas en esta cuenta.</p>
              )}

              {transactions.length > 0 && (
                <ul className="tx-list">
                  {transactions.map((t) => (
                    <li key={t.id} className="tx-row">
                      <div className="tx-row-main">
                        <span className="tx-row-name">{t.name}</span>
                        <span className="tx-row-meta">
                          {t.category?.name ?? 'General'} {t.merchant ? `· ${t.merchant}` : ''}
                        </span>
                      </div>
                      <span className={`tx-row-amount ${t.transactionType === 'INCOME' ? 'income' : 'expense'}`}>
                        {t.transactionType === 'INCOME' ? '+' : '-'}
                        {currency(t.amount)}
                      </span>
                    </li>
                  ))}
                </ul>
              )}
            </section>
          </div>
        )}
      </main>
    </div>
  )
}
