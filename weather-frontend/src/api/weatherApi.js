export async function getCurrentWeather(city){

    const response = await fetch(
        `http://localhost:8080/weather/current?city=${encodeURIComponent(city)}`
    )

    if(!response.ok){
        throw new Error('City not found')
    }

    return await response.json()
}

export async function getForecast(city){

    const response = await fetch(
        `http://localhost:8080/weather/forecast?city=${encodeURIComponent(city)}`
    )

    if(!response.ok){
            throw new Error('Forecast not available')
        }

    return await response.json()

}