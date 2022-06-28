import axios from 'axios'

const SERVER_API_URL_MOVIES = process.env.REACT_APP_BACKEND_SERVER_URL + process.env.REACT_APP_BACKEND_MOVIE_SERVICE
const SERVER_API_URL_GENRES = process.env.REACT_APP_BACKEND_SERVER_URL + process.env.REACT_APP_BACKEND_GENRE_SERVICE

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
