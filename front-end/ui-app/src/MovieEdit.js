import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios'
import Select from 'react-select'
class MovieEdit extends Component {

  emptyItem = {
    name: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      genres: '',
      selectedGenres: [],
      newGenres:[]
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleSelect= this.handleSelect.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      let axiosResponse = await this.findBy(`${this.props.match.params.id}`);
      const movie = axiosResponse.data;
      this.setState({item: movie});
    }
    let axiosResponse = await this.findAllGenres();
    const genres = axiosResponse.data;
    let options = genres.map(function (genre) {
        return { value: genre.id, label: genre.name };
      })
    this.setState({genres: options})
  }

  findBy(id) {
    return axios.get(`http://localhost:8080/movies/${id}`,);
  }

  findAllGenres() {
    return axios.get(`http://localhost:8080/api/v1/genres`,);
  }

  save(item) {
    return axios.put(`http://localhost:8080/movies`, item);
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  handleSelect(genre) {
    console.log(genre)
    console.log({...this.state})
    genre.map(item => {
     this.state.item.genres = genre;
    })
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    await this.save(item);
    this.props.history.push('/movies');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Movie' : 'Add Movie'}</h2>;
    const genresFromMovie = []
    for (const index in item.genre) {
      genresFromMovie.push(Number(item.genre[index].id))
    }
    if (typeof this.state.genres != "string") {
      for (const index in this.state.genres) {
        if (genresFromMovie.includes(this.state.genres[index].value)) {
          this.state.selectedGenres.push(this.state.genres[index])
        }
      }
    }
    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Select
                closeMenuOnSelect={false}
                defaultValue={this.state.selectedGenres}
                isMulti
                onChange={this.handleSelect}
                options={this.state.genres}
            />
          </FormGroup>
          <FormGroup>
            <Label for="year">Year</Label>
            <Input type="text" name="year" id="year" value={item.year || ''}
                   onChange={this.handleChange} autoComplete="year"/>
          </FormGroup>
          <FormGroup>
            <Label for="originalTitle">Original title</Label>
            <Input type="text" name="originalTitle" id="originalTitle" value={item.originalTitle || ''}
                   onChange={this.handleChange} autoComplete="originalTitle"/>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="warning" tag={Link} to="/movies">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(MovieEdit);