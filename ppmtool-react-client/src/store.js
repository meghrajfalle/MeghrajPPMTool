import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const intialState = {};
const middleware = [thunk];

let store;
const ReactReduxDevTools =
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

if (window.navigator.userAgent.includes("Chrome") && ReactReduxDevTools) {
  // if the browser is chrome and react devtools are present then we load the store with the react dev tools
  //else open it with the middleware.
  store = createStore(
    rootReducer,
    intialState,
    compose(applyMiddleware(...middleware), ReactReduxDevTools)
  );
} else {
  store = createStore(
    rootReducer,
    intialState,
    compose(applyMiddleware(...middleware))
  );
}

export default store;
