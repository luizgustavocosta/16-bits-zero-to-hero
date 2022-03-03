import React, {Component} from 'react'
import AuthenticationService from '../service/AuthenticationService';

import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';

class LoginComponent extends Component {

  constructor(props) {
    super(props)

    this.state = {
      username: '',
      password: '',
      hasLoginFailed: false,
      showSuccessMessage: false
    }

    this.handleChange = this.handleChange.bind(this)
    this.loginClicked = this.loginClicked.bind(this)
  }

  handleChange(event) {
    this.setState(
      {
        [event.target.name]: event.target.value
      }
    )
  }

  loginClicked() {
    AuthenticationService
      .executeBasicAuthenticationService(this.state.username, this.state.password)
      .then(() => {
        AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password)
        this.props.history.push(`/movies`)
      }).catch(() => {
      this.setState({showSuccessMessage: false})
      this.setState({hasLoginFailed: true})
    })

  }

  render() {
    return (
      <div>
        <div className="container">
          {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials for {this.state.username}</div>}
          {this.state.showSuccessMessage && <div>Login Successful</div>}
          <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
              sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
              }}
            >
              <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                <LockOutlinedIcon />
              </Avatar>
              <Typography component="h1" variant="h5">
                Sign in
              </Typography>
          <TextField
              margin="normal"
              required
              fullWidth
              id="username"
              label="User"
              name="username"
              value={this.state.username}
              autoFocus
              onChange={this.handleChange}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              onChange={this.handleChange}
              onKeyPress={(event) => event.key === 'Enter' && this.loginClicked()}
            />

              <Button
                type="submit"
                variant="contained"
                onClick={this.loginClicked}
                sx={{ mt: 3, mb: 2 }}
              >
                Sign In
              </Button>
            </Box></Container>
        </div>
      </div>
    )
  }
}

export default LoginComponent