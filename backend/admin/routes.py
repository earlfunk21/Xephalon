from backend.admin import admin_bp
from backend.models import User, db
import click


@admin_bp.cli.command("create-user")
@click.argument("username", nargs=1)
@click.argument("password", nargs=1)
def create_user(username, password):
    user = User(username=username, password=password)
    db.session.add(user)
    print("successfully created")


@admin_bp.cli.command("search-username")
@click.argument("username", nargs=1)
def search_username(username):
    users = User.query.search_username(username).all()
    print(users)
