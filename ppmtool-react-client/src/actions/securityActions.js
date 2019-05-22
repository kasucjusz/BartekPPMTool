import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const login = LoginRequest => async dispatch => {
  try {
    //post login request
    const res = await axios.post("/api/users/login", LoginRequest);
    //extract the token from the res.data
    const { token } = res.data;
    //store the token in the localStorage
    localStorage.setItem("jwtToken", token);
    //set token in headers
    setJWTToken(token);
    //decode the token on React
    const decoded = jwt_decode(token);
    //dispatch to securityReducer

    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export function logout() {
  return dispatch => {
    localStorage.removeItem("jwtToken");
    setJWTToken(false);
    dispatch({
      type: SET_CURRENT_USER,
      payload: {}
    });
    //  window.location.reload();///jak to zrobic inaczej zeby nie odwiezalo strony tylko z automatu zmienialo sie?
    window.location.href = "/landing"; //update, dziala
  };
}
