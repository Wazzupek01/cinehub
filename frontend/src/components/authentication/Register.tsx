import { useState } from "react";
import classes from "./Login.module.css";
import AuthenticationService from "../../services/AuthenticationService";
import { useNavigate } from "react-router-dom";
import { IInput } from "../../interfaces/IInput";
import {
  regexEmail,
  regexPassword,
  regexUsername,
} from "../../helpers/validation-constants";
import Input from "../ui/Input";
import { IAuthenticationResponse } from "../../interfaces/IAuthenticationResponse";

function Register() {
  const navigate = useNavigate();

  const [nickname, setNickname] = useState<IInput>({
    value: "",
    isValid: null,
  });
  const [email, setEmail] = useState<IInput>({
    value: "",
    isValid: null,
  });
  const [password, setPassword] = useState<IInput>({
    value: "",
    isValid: null,
  });

  const nicknameChangeHandler = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setNickname({
      value: event.target.value,
      isValid: regexUsername.test(event.target.value),
    });
  };

  const emailChangeHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail({
      value: event.target.value,
      isValid: regexEmail.test(event.target.value),
    });
  };

  const passwordChangeHandler = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setPassword({
      value: event.target.value,
      isValid: regexPassword.test(event.target.value),
    });
  };
  const authService = new AuthenticationService();

  const handleRegister = async (event: React.SyntheticEvent) => {
    event.preventDefault();
    const authenticationResponse: IAuthenticationResponse = JSON.parse(
      await authService.registerUser(
        nickname.value,
        email.value,
        password.value
      )
    );

    const {nickname: nick, role} = authenticationResponse
        
    if (nick !== null && nick !== "") {
      localStorage.setItem("nickname", nick!);
      localStorage.setItem("role", role!);
      navigate("/");
    }
  };

  return (
    <div className={classes.Login_container}>
      <h1>Register new account</h1>
      <form onSubmit={handleRegister}>
        <Input
          name="Nickname"
          type="text"
          placeholder="Nickname"
          onChange={nicknameChangeHandler}
          isValid={nickname.isValid}
          id="Nickname"
        />
        <br />
        <Input
          name="Email"
          type="email"
          placeholder="email"
          onChange={emailChangeHandler}
          isValid={email.isValid}
          id="Email"
        />
        <br />
        <Input
          name="Password"
          type="password"
          placeholder="Password"
          onChange={passwordChangeHandler}
          isValid={password.isValid}
          id="Password"
        />
        <br />
        <input type="submit" value="Register" />
      </form>
    </div>
  );
}

export default Register;
