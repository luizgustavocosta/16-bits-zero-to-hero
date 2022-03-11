import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import axios from 'axios'
import Modal from './Modal.js';

class NewUser extends Component {

  emptyUser = {
    name: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      user: this.emptyUser,
      show: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

  }

  showModal = () => {
    this.setState({ show: true });
  }

  hideModal = () => {
    this.setState({ show: false });
  }

  save(user) {
    return axios.put(`http://localhost:8080/user`, user);
  }


  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let user = {...this.state.user};
    user[name] = value;
    this.setState({user});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {user} = this.state;
    await this.save(user);
    this.props.history.push('/movies');
  }

  render() {
    const {user} = this.state;
    const title = <h2>{'Glad to see you here'}</h2>;
    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={user.name || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="button" onClick={this.showModal}>Save</Button>{''}
            <Button color="warning" tag={Link} to="/movies">Cancel</Button>

            <Modal show={this.state.show} handleClose={this.hideModal}>
              <p>Operation not supported</p>
            </Modal>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(NewUser);