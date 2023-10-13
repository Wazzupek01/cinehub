import { useNavigate } from "react-router-dom";
import classes from "./Login.module.css";
import { useState } from "react";
import AuthenticationService from "../../services/AuthenticationService";
import Input from "../ui/Input";
import { regexEmail, regexPassword } from "../../helpers/validation-constants";
import { IInput } from "../../interfaces/IInput"

const Login = () => {

  const [error, setError] = useState<string>("");

  const [email, setEmail] = useState<IInput>({
    value: "",
    isValid: null,
  });
  const [password, setPassword] = useState<IInput>({
    value: "",
    isValid: null,
  });

  const emailChangeHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail({
      value: event.target.value,
      isValid: regexEmail.test(event.target.value),
    });
  };

  const passwordChangeHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword({
      value: event.target.value,
      isValid: regexPassword.test(event.target.value),
    });
  };

  const navigate = useNavigate();

  const authService = new AuthenticationService();

  const handleLogin = async (event: React.SyntheticEvent) => {
    event.preventDefault();
    if (email.isValid && password.isValid) {
      const nickname = await authService.loginUser(email.value, password.value);
      if (nickname !== "") {
        localStorage.setItem("nickname", nickname);
        navigate("/");
      } else  {
          setError("Wrong credentials!")
      }
    }
  };

  return (
    <div className={classes.Login_container}>
      <h1>Login to existing account</h1>
      <form onSubmit={handleLogin}>
        <Input
          name="Email"
          type="email"
          placeholder="email"
          onChange={emailChangeHandler}
          isValid={email.isValid}
          id="Email" 
          />
        <Input
          name="Password"
          type="password"
          placeholder="Password"
          onChange={passwordChangeHandler}
          isValid={password.isValid}
          id="Password"    
          />
        <input type="submit" value="Login" />
        <span style={{color: "red", maxHeight: "1em", minHeight: "1em"}}>{error}</span>
      </form>
    </div>
  );
}

export default Login;
