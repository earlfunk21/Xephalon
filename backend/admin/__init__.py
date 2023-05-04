from flask import Blueprint


admin_bp = Blueprint("admin", __name__, cli_group=None)

from backend.admin import routes
