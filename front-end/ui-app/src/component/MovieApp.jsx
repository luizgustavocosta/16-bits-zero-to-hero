import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import LoginComponent from './LoginComponent';
import LogoutComponent from './LogoutComponent';
import MovieListComponent from './MovieListComponent';
import NewUserComponent from './NewUserComponent';
import MenuComponent from './MenuComponent';
import AuthenticatedRoute from './AuthenticatedRoute';
import MovieEdit from './MovieEditComponent';

class MovieApp extends Component {
    render() {
        return (
            <>
                <Router>
                    <>
                        <MenuComponent/>
                        <Switch>
                            <Route path="/" exact component={LoginComponent}/>
                            <Route path="/login" exact component={LoginComponent}/>
                            <Route path="/newUser" exact component={NewUserComponent}/>
                            <AuthenticatedRoute path='/movies/:id' component={MovieEdit}/>
                            <AuthenticatedRoute path="/logout" exact component={LogoutComponent}/>
                            <AuthenticatedRoute path="/movies" exact component={MovieListComponent}/>
                        </Switch>
                    </>
                </Router>
            </>
        )
    }
}

export default MovieApp