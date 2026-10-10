import { useState } from 'react'
import {registerUser} from '../api/authApi'

function Register(){

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const [success, setSuccess] = useState('')

    async function handleRegister(event){
        event.preventDefault()

        try{
            await registerUser(email, password)

            setSuccess('Registration successful!')
            setError('')
        }catch(error){
            setError(error.message)
            setSuccess('')

        }


    }


    return(
        <div>
            <h2>Register</h2>

            <form onSubmit={handleRegister}>

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
                    minLength={8}
                    required
                />

                <button type="submit">Register</button>

            </form>

            {error && <p>{error}</p>}
            {success && <p>{success}</p>}

        </div>
        )
    }

export default Register