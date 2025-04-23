import React, { useState } from 'react';
import { TextField, Button, Typography, Grid, IconButton, InputAdornment } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';  // Eye icons for password visibility
import { useNavigate } from 'react-router-dom';
import { login } from '../services/AuthService'; // Assuming you already have the login service
import '../styles/LoginView.css';

const LoginView = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false); // To toggle password visibility
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Check credentials using the login service
            await login(email, password);
            navigate('/dashboard'); // Redirect to dashboard on successful login
        } catch (error) {
            console.error("Login failed:", error);
        }
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <div className="login-container">
            <div className="login-form-wrapper">
                <Typography variant="h4" className="login-heading">
                    Login
                </Typography>
                <Typography variant="h6" className="login-subheading">
                    Please enter your credentials.
                </Typography>

                <form onSubmit={handleSubmit} style={{ width: '100%' }}>
                    <TextField
                        label="Username"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <TextField
                        label="Password"
                        type={showPassword ? 'text' : 'password'}  // Toggle password visibility
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton onClick={togglePasswordVisibility} edge="end">
                                        {showPassword ? <VisibilityOff /> : <Visibility />}
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                    <Button type="submit" variant="contained" color="primary" fullWidth>
                        Sign In
                    </Button>
                </form>
            </div>
        </div>
    );
};

export default LoginView;
