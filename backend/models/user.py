from backend.models import db
from werkzeug.security import generate_password_hash, check_password_hash
from flask_sqlalchemy import BaseQuery


class Query(BaseQuery):
    def get_user(self, username: str):
        user = self.filter(User.username == username)
        return user

    def search_username(self, username: str):
        users = self.filter(User.username.like(f"%{username}%"))
        return users


class User(db.Model):
    query_class = Query
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True)
    password = db.Column(db.String(255))

    def __init__(self, username: str, password: str) -> None:
        self.username = username.lower()
        self.set_password(password)

    def __str__(self) -> str:
        return f"@{self.username}"

    def __repr__(self) -> str:
        return f"@{self.username}"

    def set_password(self, password):
        self.password = generate_password_hash(password)

    def check_password(self, password):
        return check_password_hash(self.password, password)

