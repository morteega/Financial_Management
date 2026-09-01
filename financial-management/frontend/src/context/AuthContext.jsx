import { useMemo, useState } from 'react'
import { AuthContext } from './auth-context'

const STORAGE_KEY = 'finzo.user'

function readStoredUser() {
  try {
    const raw = sessionStorage.getItem(STORAGE_KEY)
    return raw ? JSON.parse(raw) : null
  } catch {
    return null
  }
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(readStoredUser)

  const value = useMemo(
    () => ({
      user,
      login(usuario) {
        setUser(usuario)
        sessionStorage.setItem(STORAGE_KEY, JSON.stringify(usuario))
      },
      logout() {
        setUser(null)
        sessionStorage.removeItem(STORAGE_KEY)
      },
    }),
    [user],
  )

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
