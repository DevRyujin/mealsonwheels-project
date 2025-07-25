
export function getUserRole() {
  const token = localStorage.getItem('token');
  if (!token) return null;

  const base64Url = token.split('.')[1];
  const payload = JSON.parse(atob(base64Url));
  return payload.role || null;
}
