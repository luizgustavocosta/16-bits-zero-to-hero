import React, {Component} from 'react'
import AuthenticationService from '../service/AuthenticationService';

import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {Alert, AlertTitle} from "@material-ui/lab";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";

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
                this.props.history.push("/movies")
            }).catch(() => {
            this.setState({showSuccessMessage: false})
            this.setState({hasLoginFailed: true})
        })
    }

    render() {
        function createUser(icon, name, password, roles, iconPermission, permission) {
            return {icon, name, password, roles, iconPermission, permission};
        }

        const users = [
            createUser('mando.png','mando', 'mando', 'Developer, Others', 'edit.png', 'Edit'),
            createUser('boba.png','boba', 'fett', 'Developer, Others', 'edit.png','Edit'),
            createUser('ironman.png','tony', 'stark', 'Developer, Others', 'edit.png','Edit'),
            createUser('james-bond-007.png','james', 'bond', 'Others', 'read.png','Read'),
        ];
        return (
            <div>
                <div className="container">
                    {this.state.hasLoginFailed &&
                        <Alert severity="error">
                            <AlertTitle>Error</AlertTitle>
                            Invalid Credentials for — <strong>{this.state.username}</strong> — Try again
                        </Alert>
                    }
                    {this.state.showSuccessMessage && <div>Login Successful</div>}
                    <Container component="main" maxWidth="xs">
                        <CssBaseline/>
                        <br/>
                        <TableContainer component={Paper}>
                            <Table sx={{minWidth: 650}} aria-label="simple table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell><b>Profile</b></TableCell>
                                        <TableCell><b>Username</b></TableCell>
                                        <TableCell><b>Password</b></TableCell>
                                        <TableCell align="center"><b>Permission</b></TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {users.map((user) => (
                                        <TableRow
                                            key={user.name}
                                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                        >
                                            <TableCell component="th" scope="row" align={"center"}>
                                                <img
                                                    alt={user.name}
                                                    loading="lazy"
                                                    width="20"
                                                    src={user.icon}
                                                />
                                            </TableCell>
                                            <TableCell component="th" scope="row">
                                                {user.name}
                                            </TableCell>
                                            <TableCell>{user.password}</TableCell>
                                            <TableCell align="center">
                                                <img
                                                    loading="lazy"
                                                    width="20"
                                                    alt={user.name}
                                                    src={user.iconPermission}
                                                />&nbsp;{user.permission}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        <Box
                            sx={{
                                marginTop: 2,
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                            }}
                        >
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
                            <Box sx={{'& button': {m: 1}}}>
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