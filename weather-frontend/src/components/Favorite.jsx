import { useState, useEffect } from 'react'
import { getFavorites, deleteFavorite } from '../api/favoriteApi'

function Favorite({token, favoriteVersion}){

    const[favorites, setFavorites] = useState([])
    const[error, setError] = useState('')

    useEffect(()=>{

        async function loadFavorites(){
            try{
                const data = await getFavorites(token)
                setFavorites(data)
                setError('')
            }catch(error){

                setError(error.message)
            }
        }

        loadFavorites()

    }, [token, favoriteVersion])

    async function handleDelete(id){

        try{
            await deleteFavorite(token, id)
            setFavorites(prev => prev.filter(fav => fav.id !== id))
            setError('')
        }catch(error){
            setError(error.message)
        }
    }


    return(

        <div>
            <h2>My favorites</h2>

            {error && <p>{error}</p>}

            {favorites.map((fav)=>(
                <div key={fav.id}>
                    <span>{fav.city},{fav.country}</span>

                    <button onClick={() => handleDelete(fav.id)}>
                        🗑️ Delete
                    </button>
                </div>
               )
            )}

        </div>
    )

}

export default Favorite