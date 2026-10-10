export async function getFavorites(token){


    const response = await fetch(
                        'http://localhost:8080/users/me/favorites',
                        {
                            method: 'GET',
                            headers:{
                                'Authorization' : `Bearer ${token}`
                            }
                        }
    )

    if(!response.ok){
        throw new Error(`HTTP error: ${response.status}`)
    }

    return await response.json()
}

export async function addFavorite(token, city, country){

    const response = await fetch(
                            'http://localhost:8080/users/me/favorites',
                            {
                                method: 'POST',
                                headers:{
                                    'Authorization' : `Bearer ${token}`,
                                    'Content-Type' : 'application/json'
                                },
                                body: JSON.stringify({
                                    city: city,
                                    country: country
                                })
                            }
    )

    if(!response.ok){
        throw new Error(`HTTP error: ${response.status}`)
    }

    return await response.json()
}

export async function deleteFavorite(token, id){

    const response = await fetch(
                                `http://localhost:8080/users/me/favorites/${id}`,
                                {
                                    method: 'DELETE',
                                    headers:{
                                        'Authorization' : `Bearer ${token}`,
                                    }
                                }
        )

        if(!response.ok){
            throw new Error(`HTTP error: ${response.status}`)
        }

}