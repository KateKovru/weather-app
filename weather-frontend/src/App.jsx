import { useState } from 'react'
import WeatherCard from './components/WeatherCard'
import ForecastCard from './components/ForecastCard'
import SearchBar from './components/SearchBar'
import {getCurrentWeather, getForecast} from './api/weatherApi'
import './App.css'
import Register from './pages/Register'
import Login from './pages/Login'
import Favorite from './components/Favorite'
import { addFavorite } from './api/favoriteApi'

function App() {

    async function searchWeather(){

        setLoading(true)
        setError('')

        try{
            const weatherData = await getCurrentWeather(city)
            const forecastData = await getForecast(city)

            setWeather(weatherData)
            setForecast(forecastData)

        }catch(error){

            setError(error.message)
            setWeather(null)
            setForecast(null)

        }finally{

            setLoading(false)

        }
    }

    async function handleAddFavorite(){

        if(!token || !weather){
            return
        }

        try{
            await addFavorite(token, weather.city, weather.country)
            setFavoriteVersion(prev => prev+1)
            alert('City added to favorites!')
        }catch(error){
            alert(error.message)
        }

    }


  const [city, setCity] = useState('')
  const [weather, setWeather] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [forecast, setForecast] = useState(null)
  const [token, setToken] = useState(
      () => localStorage.getItem('token')
  )
  const [favoriteVersion, setFavoriteVersion] = useState(0)

  return (
      <div className="app">
    <h1>Weather App</h1>

    <SearchBar setCity= {setCity} searchWeather= {searchWeather}/>

    {loading && <p>Loading...</p>}

    {error &&<p>{error}</p>}

    {weather && (
        <WeatherCard
            weather = {weather}
            onAddFavorite= {token? handleAddFavorite : null}
    />
    )}

    {forecast && <ForecastCard forecast = {forecast}/>}

    {!token && <Register />}

    {!token && <Login onLogin={(newToken) => {
                   localStorage.setItem('token', newToken)
                   setToken(newToken)
               }}/>}

    {token && (
            <div>
                <p>Logged in successfully!</p>
                <button onClick={() => {
                    localStorage.removeItem('token')
                    setToken(null)
                }}>
                    Logout
                </button>
            </div>
        )
    }

    {token && (<Favorite token={token}
                        favoriteVersion={favoriteVersion}
        />
    )}

    </div>
  )
}

export default App