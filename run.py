# import the create app application factory
from app import create_app

# import the application config classes
from config import DevelopmentConfig, ProductionConfig

app = create_app(config=DevelopmentConfig)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6901)