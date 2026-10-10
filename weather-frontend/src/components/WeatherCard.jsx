import './WeatherCard.css'

function WeatherCard({weather, onAddFavorite}){

    return(
        <div className="weather-card">

            <h2>{weather.city}, {weather.country}</h2>

            <p className="weather-temperature">
                {weather.temperature.toFixed(1)} °C
            </p>
             <div className="weather-details">
                            <div>
                                <span>Feels like</span>
                                <strong>{weather.feelsLike.toFixed(1)} °C</strong>
                            </div>

                            <div>
                                <span>Humidity</span>
                                <strong>{weather.humidity}%</strong>
                            </div>
             </div>

            {onAddFavorite && (
                <button onClick={onAddFavorite}>
                    ⭐Add to Favorites
                </button>
            )}

        </div>
        )
}

export default WeatherCard