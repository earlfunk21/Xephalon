from flask import Blueprint


admin_bp = Blueprint("admin", __name__, cli_group=None)

from app.admin import routes
