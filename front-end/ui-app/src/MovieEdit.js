import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios'

class MovieEdit extends Component {

  emptyItem = {
    name: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      let axiosResponse = await this.findBy(`${this.props.match.params.id}`);
      const movie = axiosResponse.data;
      this.setState({item: movie});
    }
  }

  findBy(id) {
    return axios.get(`http://localhost:8080/movies/${id}`,);
  }

  save(item) {
    console.log("Save item[" + item + "]");
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

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    await this.save(item);
    this.props.history.push('/movies');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Movie' : 'Add Movie'}</h2>;
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
            <Label for="genre">Genre</Label>
            <Input type="text" name="genre" id="genre" value={item.genre || ''}
                   onChange={this.handleChange} autoComplete="genre"/>
          </FormGroup>
          <FormGroup>
            <Label for="year">Year</Label>
            <Input type="text" name="year" id="genre" value={item.year || ''}
                   onChange={this.handleChange} autoComplete="year"/>
          </FormGroup>
          <FormGroup>
            <Label for="originalTitle">Original title</Label>
            <Input type="text" name="originalTitle" id="genre" value={item.originalTitle || ''}
                   onChange={this.handleChange} autoComplete="originalTitle"/>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/movies">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(MovieEdit);