function SearchBar({setCity, searchWeather}){

    return(

            <div className="search-bar">
               <input
                   placeholder="Enter city"
                   onChange={(event) => setCity(event.target.value)}
               />
               <button onClick={searchWeather}>Search</button>
            </div>
        )

    }

export default SearchBar