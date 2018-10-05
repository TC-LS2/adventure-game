import React from "react"
import { css } from "emotion"

const buttonClassName = css`
  background: var(--dark-color);
  color: var(---bold-color);
  padding: 1rem;
  text-align: center;
`

const closeClassName = css`
  cursor: pointer;
`

const Alert = ({ children, onDismiss, className }) =>
  children && (
    <div className={`${buttonClassName} ${className}`}>
      {children}
      {onDismiss && (
        <span>
          {" "}
          <span onClick={onDismiss} className={`${closeClassName}`}>
            x
          </span>
        </span>
      )}
    </div>
  )

export default Alert
