import "./Input.scss";

export default function Input() {
  return (
    <div className="signup-box">
      <div className="signup-label"># 비밀번호 재확인</div>
      <input
        className="pwInput"
        placeholder="비밀번호를 재입력해주세요."
        type="password"
        name="passwordCheck"
        //   onChange={handleInput}
      />
    </div>
  );
}
