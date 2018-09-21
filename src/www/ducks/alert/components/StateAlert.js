import { connect } from "react-redux"
import Alert from "../../../common/components/Alert"
import { clearAlert } from "../actions/clearAlert"
import getAlert from "../selectors/getAlert"

const StateAlert = connect(
  state => ({
    children: getAlert(state),
  }),
  dispatch => ({
    onDismiss: () => dispatch(clearAlert()),
  }),
)(Alert)

export default StateAlert
