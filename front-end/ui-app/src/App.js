import React, {Component} from 'react';
import './App.css';
import MovieApp from './component/MovieApp.jsx';
import Footer from './Footer'

class App extends Component {
  render() {
    return (
      <div className="container">
        <MovieApp/>
        <Footer/>
      </div>
    );
  }
}

export default App;