import * as React from 'react';
import {Component} from 'react';

import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import {FormGroup, InputLabel, OutlinedInput} from "@material-ui/core";

class NewUserComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false
        }
        this.handleClickOpen = this.handleClickOpen.bind(this)
        this.handleClose = this.handleClose.bind(this)
        this.handleAgree = this.handleAgree.bind(this)
        this.handleConfirmation = this.handleConfirmation.bind(this)
    }

    handleClickOpen = () => {
        this.setState({open: true})
    };

    handleConfirmation = () => {
        this.setState({open: true})
        this.props.history.push('/movies');
    };

    handleClose = () => {
        this.setState({open: false})
    };

    handleAgree = () => {
        this.setState({open: true});
        if (this.state.open) {
            this.props.history.push('/movies');
        }
    };

    render() {
        return (
            <div>
                <Box component="form"
                     sx={{'& > :not(style)': {m: 10, width: '25ch'},}}
                     noValidate
                     autoComplete="off">
                    <FormGroup>
                        <InputLabel htmlFor="outlined-adornment-text">First Name</InputLabel>
                        <OutlinedInput
                            id="outlined-adornment-text"
                            type={'text'}
                        />
                        <TextField
                            id="outlined-name"
                            label="Last Name"
                            sx={{'& > :not(style)': {mt: 2, mb: 3, width: '50ch'},}}
                        />
                        <InputLabel htmlFor="outlined-adornment-password">Password</InputLabel>
                        <OutlinedInput
                            id="outlined-adornment-password"
                            type={'password'}
                            label="Password"
                        />
                    </FormGroup>

                </Box>
                <Button variant="contained" onClick={this.handleClickOpen} title="Save">
                    Save
                </Button>
                <Dialog
                    title="confirmationDialog"
                    open={this.state.open}
                    keepMounted
                    aria-describedby="alert-dialog-slide-description"
                >
                    <DialogTitle>{"Work in progress....."}</DialogTitle>
                    <DialogContent>
                        <DialogContentText id="alert-dialog-slide-description">
                            Well, even after the confirmation
                            nothing will happen, since this is an experimental feature
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose}>Cancel</Button>
                        <Button onClick={this.handleConfirmation}>Confirm</Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}

export default NewUserComponent
