import axios from 'axios'
import application from './../application.json'
const SERVER_API_URL_MOVIES = application.SERVER_URL + application.MOVIE_SERVICE
const SERVER_API_URL_GENRES = application.SERVER_URL + application.GENRE_SERVICE

class BackendService {

    retrieveAllMovies() {
        console.log(SERVER_API_URL_MOVIES)
        return axios.get(`${SERVER_API_URL_MOVIES}`,);
    }

    delete(id) {
        return axios.delete(`${SERVER_API_URL_MOVIES}/${id}`,);
    }

    findBy(id) {
        return axios.get(`${SERVER_API_URL_MOVIES}/${id}`,);
    }

    findAllGenres() {
        return axios.get(`${SERVER_API_URL_GENRES}`,);
    }

    save(movie) {
        return axios.put(`${SERVER_API_URL_MOVIES}`, movie);
    }

}

export default new BackendService()
