# Challenge Uala
Mobile coding challenge from Uala for open mobile developer position

## Features

- Search cities and filter by favorites
- Mark/unmark favorite cities
- Paginated city list
- View selected city on Google Maps
- UI and ViewModel unit tests

## Tech Stack

- Jetpack Compose
- Hilt for dependency injection
- ViewModel + StateFlow
- Retrofit
- Navigation Compose 2
- Room Database
- Google Maps SDK
- Coroutine + Flow
- MockK for unit testing

## Architecture

- MVVM (Model-View-ViewModel)
- Modular & Composable UI Components

## 📦 Project Structure

├── ui/
│   ├── components/
│   │   ├── SearchBarComponent.kt
│   │   ├── CityListComponent.kt
│   │   ├── MapComponent.kt
│   │   ├── ErrorComponent.kt
│   │   └── RowCityComponent.kt
│   └── screens/
│       ├── CityListScreen.kt
│       ├── LandscapeListMapScreen.kt
│       └── MapScreen.kt
├── viewmodel/
│   ├── CityViewModel.kt
│   └── MapViewModel.kt
├── data/
│   ├── CityApi.kt
│   ├── CityRepository.kt
│   ├── CityEntity.kt
│   ├── CityResponse.kt
│   ├── CountryResponse.kt
│   ├── CountryRepository.kt
│   └── CityDao.kt
├── model/
│   ├── CityDTO.kt
│   └── CountryDTO.kt
├── di/
│   └── AppModule.kt
├── MainActivity.kt
├── navigation/
│   ├── screens/
│   │    ├── CityList.kt
│   │    └── Map.kt
│   └── NavigationWrapper.kt
└── test/
    ├── ui/
    │   ├── SearchBarTest.kt
    │   └── CityListTest.kt
    └── viewmodel/
        ├── CityViewModelTest.kt
        └── MapViewModelTest.kt

##  Notes

It's one of my first experiences with jetpack compose besides online courses. The UI's are not the best and I kept to the minimum consulting to tools such as IAs.
I preferred to deliver a not so good jetpack compose code so you can se where are my flaws in this technology and check where i need to improve.

If possible, I would appreciate a feedback on the jetpack compose side. I will help me a lot to improve my skills in this area.

