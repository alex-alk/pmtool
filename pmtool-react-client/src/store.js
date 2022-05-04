//import { applyMiddleware, compose } from "redux";
import { configureStore } from "@reduxjs/toolkit";
//import thunk from "redux-thunk";
import reducer from "./reducers";

const initialState = {};
const store = configureStore({
  reducer,
  initialState,
});
// const middleware = [thunk];

// let store;

// const middlewareEnhancer = applyMiddleware(...middleware);

// if (window.navigator.userAgent.includes("Chrome")) {
//   const middlewareEnhancer = applyMiddleware(...middleware);
//   const composedEnhancers = compose(
//     middlewareEnhancer,
//     window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
//   );

//   store = configureStore({
//     reducer,
//     initialState,
//     composedEnhancers,
//   });
// } else {
//   store = configureStore({
//     reducer,
//     initialState,
//     middleware: compose(middlewareEnhancer),
//   });
// }

export default store;
