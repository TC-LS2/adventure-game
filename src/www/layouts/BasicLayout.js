import React from "react"
import { Link } from "react-router-dom"
import { css } from "emotion"

import wrapperClassName from "../common/styles/wrapperClassName"
import StateAlert from "../ducks/alert/components/StateAlert"

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
