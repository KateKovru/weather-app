import { useState } from 'react'
import { loginUser } from '../api/authApi'

function Login({onLogin}){

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')

    async function handleLogin(event){
        event.preventDefault()

        try{
            const data = await loginUser(email, password)


            onLogin(data.token)
            setError('')

        }catch(error){

            setError(error.message)
        }

    }

    return(

        <div>
            <h2>Login</h2>

            <form onSubmit={handleLogin}>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                    required
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    required
                />

                <button type="submit">Login</button>
            </form>

            {error && <p>{error}</p>}

        </div>
    )
}

export default Login