import React from "react";
import { Route, Navigate, useParams } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";

// const SecureRoute = ({ component: Component, security, ...otherProps }) => (
//   <Route
//     {...otherProps}
//     element={(props) =>
//       security.validToken === true ? (
//         <Component {...props} />
//       ) : (
//         <Navigate to="/login" />
//       )
//     }
//   />
// );

function SecureRoute({ children, ...props }) {
  let isAuthenticated = props.security.validToken === true;
  return isAuthenticated ? children : <Navigate to="/login" />;
}

SecureRoute.propTypes = {
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps)(SecureRoute);
