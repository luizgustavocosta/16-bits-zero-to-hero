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
import {Alert, AlertTitle} from "@material-ui/lab";

class LoginComponent extends Component {

  constructor(props) {
    super(props)

    this.state = {
      username: '',
      password: '',
      roles: '',
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
      .then(value => {
        AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password, value.data.roles)
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
          {this.state.hasLoginFailed &&
            <Alert severity="error">
              <AlertTitle>Error</AlertTitle>
              Invalid Credentials for  — <strong>{this.state.username}</strong> — Try again
            </Alert>
          }
          {this.state.showSuccessMessage && <div>Login Successful</div>}
          <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <div>
              <table>
                <th>User</th>
                <th>Password</th>
                <th>Roles</th>
                <tr>
                  <td>luiz</td>
                  <td>costa</td>
                  <td>Developer, Admin</td>
                </tr>
                <tr>
                  <td>ben</td>
                  <td>benspassword</td>
                  <td>Admin</td>
                </tr>
              </table>
            </div>
            <Box
              sx={{
                marginTop: 2,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
              }}
            >
              <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                <LockOutlinedIcon/>
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
              <Box sx={{ '& button': { m: 1 } }}>
                <Button
                  type="submit"
                  variant="contained"
                  onClick={this.loginClicked}
                  sx={{mt: 1, mb: 1, mr: 30}}>
                  Sign In
                </Button>
                <Button
                  type="submit"
                  variant="outlined"
                  sx={{mt: 1, mb: 1, ml: 10}}
                  href={"/newUser"}>
                  Create account
                </Button>
              </Box>
            </Box></Container>
        </div>
      </div>
    )
  }
}

export default LoginComponent