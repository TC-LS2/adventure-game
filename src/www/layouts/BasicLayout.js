import React from "react"
import { Link } from "react-router-dom"
import { css } from "emotion"

import wrapperClassName from "../common/styles/wrapperClassName"
import StateAlert from "../ducks/alert/components/StateAlert"
import packageJson from "../../../package.json"

const headerClassName = css`
  background: green;
  color: black;
  padding: 1.41em 0;
`

const navLinkClassName = css`
  padding-right: 1em;
  text-decoration: none;
`

const mainClassName = css`
  padding-right: 1em;
`

const NavLink = props => <Link className={navLinkClassName} {...props} />

const BasicLayout = ({ children, breadCrumb, footer }) => (
  <div>
    <div className={headerClassName}>
      <div
        style={{
          marginTop: "-1.41em",
          textAlign: "right",
        }}
      >
        <small>
          {packageJson.name} <em>v{packageJson.version}</em>
        </small>
      </div>
      <div className={wrapperClassName}>
        <NavLink to="/">Home</NavLink>
        <NavLink to="/game">Game</NavLink>
        <NavLink to="/blog">Blog</NavLink>
        <NavLink to="/builder">Builder</NavLink>
      </div>
    </div>
    <div>
      <StateAlert />
    </div>
    <div className={wrapperClassName}>
      {breadCrumb && (
        <div>
          <Link to="/">Home</Link> {breadCrumb}
        </div>
      )}
      <div className={mainClassName}>{children}</div>
      {footer}
    </div>
  </div>
)

export default BasicLayout
