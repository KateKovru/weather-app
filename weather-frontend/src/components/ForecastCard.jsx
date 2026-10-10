import './ForecastCard.css'

function ForecastCard({forecast}){

    console.log('Forecast item:',  forecast.forecasts[0])

    return(

         <div className="forecast">
                <h2>Forecast for {forecast.city}</h2>
            <div className="forecast-grid">
                {forecast.forecasts.slice(0, 12).map((item, index) => (
                  <div className="forecast-item" key={index}>
                    <p className="forecast-date"> {item.time.slice(5, 10)} · {item.time.slice(11, 16)}</p>
                    <h3>{item.temperature.toFixed(1)} °C</h3>
                    <p>💧{item.humidity}%</p>
                    <p>{item.description}</p>
                  </div>
                ))}
              </div>
            </div>
        )

    }

export default ForecastCard