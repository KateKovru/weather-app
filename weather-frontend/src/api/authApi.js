export async function registerUser(email, password){

    const response = await fetch(
                            `http://localhost:8080/auth/register`,
                            {
                                method: 'POST',
                                headers: {
                                    'Content-Type' : 'application/json'
                                },

                                body: JSON.stringify({
                                    email: email,
                                    password: password
                                })

                            }
                          )

    if(!response.ok){
        throw new Error('Registration failed')
    }

}

export async function loginUser(email, password){

    const response = await fetch(
                       `http://localhost:8080/auth/login`,
                       {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },

                            body: JSON.stringify({
                                email: email,
                                password: password
                            })
                       }
    )

    if(!response.ok){
        throw new Error('Invalid email or password')
    }

    return await response.json()
}