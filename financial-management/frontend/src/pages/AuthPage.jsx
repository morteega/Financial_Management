import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { registerUser, loginUser } from '../services/authApi'
import { useAuth } from '../hooks/useAuth'
import './AuthPage.css'

const EMAIL_RE = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

function MailIcon() {
  return (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
      <rect x="3" y="5" width="18" height="14" rx="2.5" />
      <path d="m4 7 8 6 8-6" strokeLinecap="round" strokeLinejoin="round" />
    </svg>
  )
}

function LockIcon() {
  return (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
      <rect x="4.5" y="10.5" width="15" height="9.5" rx="2.2" />
      <path d="M8 10.5V8a4 4 0 1 1 8 0v2.5" strokeLinecap="round" />
    </svg>
  )
}

function EyeIcon({ off }) {
  return (
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
      <path
        d="M2.5 12S6 5.5 12 5.5 21.5 12 21.5 12 18 18.5 12 18.5 2.5 12 2.5 12Z"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <circle cx="12" cy="12" r="2.7" />
      {off && <path d="M4 4 20 20" strokeLinecap="round" />}
    </svg>
  )
}

function SpinnerIcon() {
  return (
    <svg className="spinner" viewBox="0 0 24 24" fill="none">
      <circle cx="12" cy="12" r="9" stroke="currentColor" strokeOpacity="0.25" strokeWidth="3" />
      <path d="M21 12a9 9 0 0 0-9-9" stroke="currentColor" strokeWidth="3" strokeLinecap="round" />
    </svg>
  )
}

export default function AuthPage() {
  const navigate = useNavigate()
  const { login } = useAuth()

  const [mode, setMode] = useState('login') // 'login' | 'register'
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [showPassword, setShowPassword] = useState(false)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [notice, setNotice] = useState(null)

  const isRegister = mode === 'register'
  const emailValid = EMAIL_RE.test(email)
  const passwordValid = password.length >= 6
  const canSubmit = emailValid && passwordValid && !loading

  function switchMode(next) {
    if (next === mode) return
    setMode(next)
    setError(null)
    setNotice(null)
  }

  async function handleSubmit(e) {
    e.preventDefault()
    if (!canSubmit) return

    setLoading(true)
    setError(null)
    setNotice(null)

    try {
      if (isRegister) {
        await registerUser(email, password)
        setNotice('Cuenta creada con éxito. Ahora puedes iniciar sesión.')
        setMode('login')
        setPassword('')
      } else {
        const usuario = await loginUser(email, password)
        login(usuario)
        navigate('/transacciones', { replace: true })
      }
    } catch (err) {
      setError(err.message || 'Ocurrió un error inesperado')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-screen">
      <div className="auth-backdrop" aria-hidden="true">
        <span className="glow glow-a" />
        <span className="glow glow-b" />
      </div>

      <div className="auth-shell">
        <aside className="auth-brand">
          <div className="brand-mark">
            <svg viewBox="0 0 32 32" fill="none">
              <rect width="32" height="32" rx="9" fill="url(#g)" />
              <path
                d="M8 20.5 13 15l4 3.5L24 10"
                stroke="#0b1220"
                strokeWidth="2.2"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
              <path d="M19 10h5v5" stroke="#0b1220" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round" />
              <defs>
                <linearGradient id="g" x1="0" y1="0" x2="32" y2="32">
                  <stop stopColor="#34e0a1" />
                  <stop offset="1" stopColor="#22c1c3" />
                </linearGradient>
              </defs>
            </svg>
            <span>Finzo</span>
          </div>

          <h1>Controla cada peso de tus finanzas.</h1>
          <p className="brand-copy">
            Presupuestos, gastos y cuentas en un solo lugar, con una vista clara de hacia
            dónde va tu dinero.
          </p>

          <ul className="brand-stats">
            <li>
              <span className="stat-value">+30%</span>
              <span className="stat-label">ahorro promedio</span>
            </li>
            <li>
              <span className="stat-value">100%</span>
              <span className="stat-label">bajo tu control</span>
            </li>
          </ul>

          <div className="brand-chart" aria-hidden="true">
            <svg viewBox="0 0 220 80" fill="none">
              <path
                d="M4 60 L34 46 L64 54 L94 30 L124 38 L154 14 L184 24 L216 6"
                stroke="#34e0a1"
                strokeWidth="2.5"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
              <path
                d="M4 60 L34 46 L64 54 L94 30 L124 38 L154 14 L184 24 L216 6 L216 80 L4 80 Z"
                fill="url(#area)"
                opacity="0.35"
              />
              <defs>
                <linearGradient id="area" x1="0" y1="0" x2="0" y2="80">
                  <stop stopColor="#34e0a1" />
                  <stop offset="1" stopColor="#34e0a1" stopOpacity="0" />
                </linearGradient>
              </defs>
            </svg>
          </div>
        </aside>

        <main className="auth-card">
          <div className="auth-tabs" role="tablist">
            <button
              type="button"
              role="tab"
              aria-selected={!isRegister}
              className={!isRegister ? 'active' : ''}
              onClick={() => switchMode('login')}
            >
              Iniciar sesión
            </button>
            <button
              type="button"
              role="tab"
              aria-selected={isRegister}
              className={isRegister ? 'active' : ''}
              onClick={() => switchMode('register')}
            >
              Crear cuenta
            </button>
            <span className={`auth-tabs-indicator ${isRegister ? 'right' : ''}`} />
          </div>

          <header className="auth-header">
            <h2>{isRegister ? 'Crea tu cuenta' : 'Accede a tu cuenta'}</h2>
            <p>
              {isRegister
                ? 'Empieza a organizar tus finanzas en minutos.'
                : 'Ingresa tus credenciales para continuar.'}
            </p>
          </header>

          <form className="auth-form" onSubmit={handleSubmit} noValidate>
            <label className="field">
              <span className="field-label">Correo electrónico</span>
              <span className="field-control">
                <span className="field-icon">
                  <MailIcon />
                </span>
                <input
                  type="email"
                  autoComplete="email"
                  placeholder="tucorreo@ejemplo.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </span>
            </label>

            <label className="field">
              <span className="field-label">Contraseña</span>
              <span className="field-control">
                <span className="field-icon">
                  <LockIcon />
                </span>
                <input
                  type={showPassword ? 'text' : 'password'}
                  autoComplete={isRegister ? 'new-password' : 'current-password'}
                  placeholder="Mínimo 6 caracteres"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <button
                  type="button"
                  className="field-toggle"
                  onClick={() => setShowPassword((v) => !v)}
                  aria-label={showPassword ? 'Ocultar contraseña' : 'Mostrar contraseña'}
                >
                  <EyeIcon off={showPassword} />
                </button>
              </span>
            </label>

            {error && <p className="form-message error">{error}</p>}
            {notice && !error && <p className="form-message notice">{notice}</p>}

            <button type="submit" className="auth-submit" disabled={!canSubmit}>
              {loading ? <SpinnerIcon /> : isRegister ? 'Crear cuenta' : 'Iniciar sesión'}
            </button>
          </form>

          <p className="auth-switch">
            {isRegister ? '¿Ya tienes cuenta?' : '¿Aún no tienes cuenta?'}{' '}
            <button type="button" onClick={() => switchMode(isRegister ? 'login' : 'register')}>
              {isRegister ? 'Inicia sesión' : 'Regístrate'}
            </button>
          </p>
        </main>
      </div>
    </div>
  )
}
