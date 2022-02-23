import React, { Component } from 'react'
import CourseDataService from '../service/MovieDataService.js';

const USER = 'in28minutes'; //Change to be dynamic

class ListMoviesComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            movies: [],
            message: null
        }
        this.refreshCourses = this.refreshCourses.bind(this)
    }

    componentDidMount() {
        this.refreshCourses();
    }

    refreshCourses() {
        CourseDataService.retrieveAllMovies(USER)
            .then(
                response => {
                    this.setState({ movies: response.data })
                }
            )
    }


    render() {
        console.log('render')
        return (
            <div className="container">
                <h3>All Courses</h3>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.movies.map(
                                    movie =>
                                        <tr key={movie.id}>
                                            <td>{movie.id}</td>
                                            <td>{movie.description}</td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default ListMoviesComponent
